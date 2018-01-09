package businessLogic;

import org.hibernate.SessionFactory;

public interface IDatabaseStore {

	/**
	 * 
	 * @param sessionFactory
	 */
	void save(SessionFactory sessionFactory);

	/**
	 * 
	 * @param sessionFactory
	 */
	void update(SessionFactory sessionFactory);

	/**
	 * 
	 * @param sessionFactory
	 */
	void delete(SessionFactory sessionFactory);

	/**
	 * 
	 * @param idComponents
     * @param sessionFactory the value of sessionFactory
	 */
	void getByID(int[] idComponents, SessionFactory sessionFactory);

}