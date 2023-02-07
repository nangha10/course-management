package com.example.teacherservice.dto;

public class MessageCode {
    public static final int SUCCESSFUL = 200;
    public static final String SUCCESSFUL_MESSAGE = "Thành công";

    public static final int FAILED = 0;
    public static final String FAILED_MESSAGE = "Thất bại";

    public static final int TEACHER_CODE_EXISTED = 1000;
    public static final String TEACHER_CODE_EXISTED_MESSAGE = "Mã giáo viên đã tồn tại";

    public static final int TEACHER_NOT_EXISTED = 1001;
    public static final String TEACHER_NOT_EXISTED_MESSAGE = "Giáo viên không tồn tại";

    public static final int LIST_IS_EMPTY = 1002;
    public static final String LIST_IS_EMPTY_MESSAGE = "Danh sách trống";

}
