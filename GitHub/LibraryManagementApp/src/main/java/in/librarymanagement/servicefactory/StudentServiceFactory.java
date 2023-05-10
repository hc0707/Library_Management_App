package in.librarymanagement.servicefactory;

import in.librarymanagement.service.IStudentService;
import in.librarymanagement.service.StudentServiceImpl;

public class StudentServiceFactory {
    private static IStudentService studentService=null;

    private StudentServiceFactory() {
    }
    public static IStudentService getStudentService(){
        if (studentService==null) {
            studentService= new StudentServiceImpl();
        }
        return studentService;
    }
    
}
