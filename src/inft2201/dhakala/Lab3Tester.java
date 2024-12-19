package inft2201.dhakala;

import java.sql.Connection;
//import inft2201.kunza.utils.DatabaseConnect;
//import inft2201.kunza.exceptions.*;
//import inft2201.kunza.models.*;

public class Lab3Tester {

    /** 
     * @param args
     */
    public static void main(String[] args) {

        Connection c = null;
        c = DatabaseConnect.initialize();
        Student.initialize(c);

        System.out.println("AUTHENTICATION TESTS \n");

        // test student authenticator
        System.out.println("*******************************************");
        System.out.println("Should be Successful - Mike Jones");
        testAuth(100111111, "password");
        System.out.println("*******************************************");
        System.out.println("Should Fail - Wrong PW");
        testAuth(100999999, "mypassword2");
        System.out.println("*******************************************");
        System.out.println("Should Fail - User not found");
        testAuth(100000000, "mypassword");
        System.out.println("*******************************************");
        System.out.println("Should be Successul - Bob");
        testAuth(100888888, "password");
        System.out.println("*******************************************");
    }

    /** 
     * @param id
     * @param pw
     */
    public static void testAuth(long id, String pw) {

        try {
            Student stud = Student.authenticate(id, pw);
            System.out.println("Authentication Successful");
            System.out.println(stud.getFirstName() + " " + stud.getLastName());
        } catch (NotFoundException nfe) {
            System.out.println("Authentication Failed: " + nfe.getMessage());
        }

    }

}
