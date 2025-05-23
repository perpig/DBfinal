package com.shu.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shu.mapper.ClassScheduleMapper;
import com.shu.mapper.EnrollmentMapper;
import com.shu.mapper.GradeMapper;
import com.shu.pojo.ClassSchedule;
import com.shu.pojo.Enrollment;
import com.shu.pojo.Grade;
import com.shu.pojo.Teacher;
import com.shu.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@WebServlet("/updateUsualGrade")
public class UpdateUsualGradeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 设置响应类型
        req.setCharacterEncoding("UTF-8");  // 设置请求编码为UTF-8
        resp.setContentType("application/json;charset=utf-8");

        // 获取当前登录的教师信息
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        if (teacher == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\": \"未登录\"}");
            return;
        }

        // 读取 JSON 数据
        BufferedReader reader = req.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        String jsonData = jsonBuilder.toString();
        if (jsonData.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"请求体为空\"}");
            return;
        }

        // 解析 JSON 数据
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> data = objectMapper.readValue(jsonData, Map.class);

        // 获取参数并正确处理类型
        Integer studentId = (Integer) data.get("studentId");
        Integer teacherId = (Integer) data.get("teacherId");
        Integer courseId = (Integer) data.get("courseId");
        String classTime = (String) data.get("classTime");
        String grade = (String) data.get("grade");

        System.out.println("(updateUsual)grade:" + grade);

        if (studentId == null || teacherId == null || courseId == null || classTime == null || grade == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"参数不能为空\"}");
            return;
        }

        // 获取SqlSession
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            // 查schedule
            ClassScheduleMapper classScheduleMapper = sqlSession.getMapper(ClassScheduleMapper.class);
            ClassSchedule schedule = classScheduleMapper.getClassScheduleByInfo(courseId, teacherId, classTime);

            // 查enrollment
            EnrollmentMapper enrollmentMapper = sqlSession.getMapper(EnrollmentMapper.class);
            Enrollment enrollment = enrollmentMapper.selectEnrollmentById(studentId, schedule.getScheduleId());

            Grade gradeObject = new Grade();
            // 转类型
            gradeObject.setUsualScore(new Integer(grade.toString()));
            gradeObject.setEnrollmentId(enrollment.getEnrollmentId());

            GradeMapper gradeMapper = sqlSession.getMapper(GradeMapper.class);
            int count = gradeMapper.updateUsualScore(gradeObject);

            if (count > 0) {
                sqlSession.commit();
                resp.getWriter().write("{\"success\": true}");
            } else {
                sqlSession.rollback();
                resp.getWriter().write("{\"success\": false, \"message\": \"修改成绩失败\"}");
            }

        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"服务器内部错误: " + e.getMessage() + "\"}");
        } finally {
            // 关闭SqlSession
            sqlSession.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}
