/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.manager;

import org.hibernate.SessionFactory;
import store.entity.NewHibernateUtil;

/**
 *
 * @author Korisnik
 */
public class ClientLoggingManager implements IClientLoggingManager {
    
    private SessionFactory sessionFactory;
    
    public ClientLoggingManager()
    {
        this.configure();
    }
    
    public void configure()
    {
         sessionFactory = NewHibernateUtil.getSessionFactory();
    }

    @Override
    public boolean tryRegister(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
