package in.librarymanagement.daofactory;

import in.librarymanagement.persistence.IStudentDao;
import in.librarymanagement.persistence.StudentDaoImpl;

public class StudentDaoFactory {
    private static IStudentDao studentDao=null;

    private StudentDaoFactory() {
    }

    public static IStudentDao getStudentDao(){
        if (studentDao==null) {
            studentDao = new StudentDaoImpl();
        }
        return studentDao;
    }
    
}
