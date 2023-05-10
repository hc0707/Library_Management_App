package in.librarymanagement.daofactory;

import in.librarymanagement.persistence.ILibrarianDao;
import in.librarymanagement.persistence.LibrarianDaoImpl;

public class LibrarianDaoFactory {
    private static ILibrarianDao librarianDao=null;

    private LibrarianDaoFactory() {
    }

    public static ILibrarianDao getLibrarainDao(){
        if (librarianDao==null) {
            librarianDao = new LibrarianDaoImpl();
        }
        return librarianDao;
    }
    
}
