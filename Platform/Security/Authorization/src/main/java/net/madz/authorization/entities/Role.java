/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.authorization.entities;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * 
 * @author CleaNEr
 */
@Entity
@Table(name = "mrole")
public class Role extends StandardObject {

    private static final long serialVersionUID = -8677837722180931934L;
    @Column(nullable = false)
    private String name;
    @ManyToMany(mappedBy = "roles")
    private final List<User> users = new LinkedList<>();
    @ManyToMany(mappedBy = "roles")
    private final List<Group> groups = new LinkedList<>();
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "TENANT_ID")
    private Tenant tenant;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups.clear();
        this.groups.addAll(groups);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
    }

    void addUserGroup(Group group) {
        if ( group == null || group.getName() == null || group.getName().trim().length() <= 0 ) {
            return;
        }
        if ( !groups.contains(group) ) {
            groups.add(group);
        }
    }

    void removeUserGroup(Group group) {
        if ( group == null || group.getName() == null || group.getName().trim().length() <= 0 ) {
            return;
        }
        if ( groups.contains(group) ) {
            groups.remove(group);
        }
    }

    void addUser(User user) {
        if ( user == null || user.getUsername() == null || user.getUsername().trim().length() <= 0 ) {
            return;
        }
        if ( !users.contains(user) ) {
            users.add(user);
        }
    }

    void removeUser(User user) {
        if ( user == null || user.getUsername() == null || user.getUsername().trim().length() <= 0 ) {
            return;
        }
        if ( users.contains(user) ) {
            users.remove(user);
        }
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
