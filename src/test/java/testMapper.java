import com.shu.dto.CourseSearchDTO;
import com.shu.mapper.CoursePlanMapper;
import com.shu.mapper.EnrollmentMapper;
import com.shu.mapper.StudentMapper;
import com.shu.mapper.TeacherMapper;
import com.shu.pojo.Course;
import com.shu.pojo.Student;
import com.shu.pojo.Teacher;
import com.shu.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testMapper {
    @Test
    public void testStudentMapper() {
        String id = "1";
        String password = "123";

        // 2.调用MyBatis完成查询
        // 这里直接去官网复制粘贴过来
        // 2.1 获取SqlSessionFactory对象 优化以后用了工具类 这样只创建一个工厂
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        // 2.2 获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2.3 获取Mapper
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        // 2.4 调用方法
        Student student = studentMapper.selectById(Integer.parseInt(id));

        System.out.println(student);
    }

    @Test
    public void testTeacherMapper() {
        String id = "1";
        String password = "123";

        // 2.调用MyBatis完成查询
        // 这里直接去官网复制粘贴过来
        // 2.1 获取SqlSessionFactory对象 优化以后用了工具类 这样只创建一个工厂
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        // 2.2 获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2.3 获取Mapper
        TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);
        // 2.4 调用方法
        Teacher teacher = teacherMapper.selectById(Integer.parseInt(id));

        System.out.println(teacher);
    }


    @Test
    public void testSearchMapper() {
        String id = "1";
        String password = "123";

        // 2.调用MyBatis完成查询
        // 这里直接去官网复制粘贴过来
        // 2.1 获取SqlSessionFactory对象 优化以后用了工具类 这样只创建一个工厂
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        // 2.2 获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2.3 获取Mapper
        EnrollmentMapper enrollmentMapper = sqlSession.getMapper(EnrollmentMapper.class);
        // 2.4 调用方法
        List<CourseSearchDTO> courseSearchDTOS = enrollmentMapper.selectEnrolledCourses(Integer.parseInt(id));

        for (CourseSearchDTO courseSearchDTO : courseSearchDTOS) {
            System.out.println(courseSearchDTO);
        }
    }

    @Test
    public void testCoursePlanMapper() {
        String id = "1";

        // 2.调用MyBatis完成查询
        // 这里直接去官网复制粘贴过来
        // 2.1 获取SqlSessionFactory对象 优化以后用了工具类 这样只创建一个工厂
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        // 2.2 获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2.3 获取Mapper
        CoursePlanMapper coursePlanMapper = sqlSession.getMapper(CoursePlanMapper.class);
        // 2.4 调用方法
        List<Course> courses = coursePlanMapper.searchCoursePlanByMajor(Integer.parseInt(id));

        for (Course course : courses) {
            System.out.println(course);
        }

    }

}
