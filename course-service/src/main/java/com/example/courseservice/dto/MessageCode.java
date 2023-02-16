package com.example.courseservice.dto;

public class MessageCode {
    public static final int SUCCESSFUL = 200;
    public static final String SUCCESSFUL_MESSAGE = "Thành công";

    public static final int FAILED = 0;
    public static final String FAILED_MESSAGE = "Thất bại";

    public static final int COURSE_CODE_EXISTED = 1000;
    public static final String COURSE_CODE_EXISTED_MESSAGE = "Mã lớp đã tồn tại";

    public static final int COURSE_NOT_EXISTED = 1001;
    public static final String COURSE_NOT_EXISTED_MESSAGE = "Lớp không tồn tại";

    public static final int LIST_IS_EMPTY = 1002;
    public static final String LIST_IS_EMPTY_MESSAGE = "Danh sách trống";

    public static final int STUDENT_NOT_EXIST = 1003;
    public static final String STUDENT_NOT_EXIST_MESSAGE = "Hoc viên không tồn tại";

    public static final int LESSON_CODE_EXISTED = 1004;
    public static final String LESSON_CODE_EXISTED_MESSAGE = "Mã buổi học đã tồn tại";

    public static final int LESSON_NOT_EXISTED = 1005;
    public static final String LESSON_NOT_EXISTED_MESSAGE = "Mã buổi học không tồn tại";

}
