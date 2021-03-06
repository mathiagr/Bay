package dao;

import entities.Group;
import entities.User;
import setup.Configuration;
import util.AuthenticationUtils;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestScoped
public class UserDao extends AbstractDao<User, String> {

    public UserDao() {
        super(User.class);
    }

    @Override
    public List<User> getAll() {
        return this.em.createQuery("SELECT item FROM User item", tClass).getResultList();
    }

    @PersistenceContext(unitName= Configuration.CURRENT_PERSISTENCE_UNIT)
    private EntityManager em;

    public User createUser(User user) {
        try {
            user.setPassword(AuthenticationUtils.encodeSHA256(user.getPassword()));
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

        Group group = new Group();
        group.setEmail(user.getEmail());
        group.setGroupname(Group.USERS_GROUP);

        em.persist(user);
        em.persist(group);

        return user;
    }

    public User findUserById(String id) {
        TypedQuery<User> query = em.createNamedQuery("findUserById", User.class);
        query.setParameter("email", id);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no users in DB
            // ignore exception and return NULL for users instead
        }
        return user;
    }
}
