package in.librarymanagement.servicefactory;

import in.librarymanagement.service.ILibrarianService;
import in.librarymanagement.service.LibrarianServiceImpl;

public class LibrarianServiceFactory {
    private static ILibrarianService librarianService=null;

    private LibrarianServiceFactory() {
    }
    public static ILibrarianService getLibrarianService(){
        if (librarianService==null) {
            librarianService= new LibrarianServiceImpl();
        }
        return librarianService;
    }
    
}
