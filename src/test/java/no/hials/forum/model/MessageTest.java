package no.hials.forum.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Mikael
 */
public class MessageTest {
    private static final String PU_NAME = "FORUM";
    
    static EntityManagerFactory factory;
    static EntityManager em;
    
    public MessageTest() {
    }

    @BeforeClass
    public static void initTestFixtures() throws Exception {
        factory = Persistence.createEntityManagerFactory(PU_NAME);
        em = factory.createEntityManager();
    }
    
    @AfterClass
    public static void closeTestFixture() {
        em.close();
        factory.close();
    }
    
    @Before
    public void createTestData() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        ForumUser user = new ForumUser("Mikael");
        em.persist(user);
        
        em.persist(new Message(user, "Message #1"));
        em.persist(new Message(user, "Message #2"));
        em.persist(new Message(user, "Message #3"));
        
        Message master = new Message(user, "Master");
        master.addReply(new Message(user,"Sub"));
        em.persist(master);
        tx.commit();
    }
    
    /**
     * Test of getId method, of class Message.
     */
    @Test
    public void testGetId() {
        List<Message> list = em.createQuery("select m from Message m where m.forumUser.name = 'Mikael'", Message.class).getResultList();
        assertEquals(5, list.size());

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Message sub = em.createQuery("select m from Message m where m.message = 'Sub'", Message.class).getSingleResult();
        System.out.println("Message id is " + sub.getId());
        em.remove(sub);

        tx.commit();
    
        list = em.createQuery("select m from Message m where m.forumUser.name = 'Mikael'", Message.class).getResultList();
        assertEquals(4, list.size());
    }
}