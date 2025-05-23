package com.shu.mapper;

import com.shu.dto.CourseSearchDTO;
import com.shu.dto.StudentSearchDTO;
import com.shu.pojo.Enrollment;
import com.shu.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EnrollmentMapper {

    /**
     * 插入选课情况 要填充student_id 和schedule_id
     * 
     * @param enrollment
     * @return
     */
    int insert(Enrollment enrollment);

    /**
     * 删除选课情况 要填充student_id 和schedule_id
     *
     * @param enrollment
     * @return
     */
    int delete(Enrollment enrollment);

    /**
     * 获取某个课程的已选人数
     * 
     * @param scheduleId 课程安排ID
     * @return 已选人数
     */
    int getEnrolledCount(Integer scheduleId);

    /**
     * 获取学生已选课程的上课时间
     * 
     * @param studentId 学生ID
     * @return 已选课程的上课时间列表
     */
    List<String> getStudentClassTimes(Integer studentId);

    /**
     * 根据学生id查询已选课程的信息包括考试和成绩
     * @param studentId
     * @return
     */
    List<CourseSearchDTO> selectEnrolledCourses(Integer studentId);

    /**
     * 方便老师查询自己班级里的娃娃的信息
     * @param ScheduleId
     * @return
     */
    List<StudentSearchDTO> selectStudentsByScheduleId(Integer ScheduleId);

    /**
     *  根据信息返回enrollment
     * @param studentId
     * @param scheduleId
     * @return
     */
    Enrollment selectEnrollmentById(@Param("studentId") Integer studentId,
                                    @Param("scheduleId") Integer scheduleId);

}