package com.miu.lms.constants;

public abstract class ApiController {
    private static final String API_VERSION = "/lms-system.azurewebsites.net/api/v1";

    public static final String AUTHENTICATE_ENDPOINT = API_VERSION + "/authenticate";
    public static final String TEACHER_ENDPOINT = API_VERSION + "/teacher";
    public static final String COURSE_ENDPOINT = API_VERSION + "/course";
    public static final String STUDENT_ENDPOINT = API_VERSION + "/student";

}
