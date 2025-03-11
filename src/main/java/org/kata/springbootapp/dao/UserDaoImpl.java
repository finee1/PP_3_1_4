package org.kata.springbootapp.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.kata.springbootapp.model.Role;
import org.kata.springbootapp.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        Set<Role> list = user.getRoles(); // Используйте getRoles(), а не getSetRoles()
        Set<Role> managedRoles = new HashSet<>();
        Role defaultRole = entityManager.createQuery(
                        "SELECT r FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", "ROLE_USER")
                .getResultStream()
                .findFirst()
                .orElse(null);
        Role adminRole = entityManager.createQuery(
                        "SELECT r FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", "ROLE_ADMIN")
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (list == null) {
            managedRoles.add(defaultRole);
        } else {
            for (Role role : list) {
                if (adminRole != null && ("ROLE_" + role.getName()).equals(adminRole.getName())) {
                    managedRoles.add(adminRole);
                    managedRoles.add(defaultRole);
                } else {
                    managedRoles.add(defaultRole);
                }
            }
        }
        user.setRoles(managedRoles);

        if (user.getId() == null) {
            user.setPassword("{noop}" + user.getPassword());
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
    }

    @Override
    public List<User> getListUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByUsername(String username) {
        List<User> users = entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public void delete(int id) {
        entityManager.remove(getUserById(id));
    }
}
