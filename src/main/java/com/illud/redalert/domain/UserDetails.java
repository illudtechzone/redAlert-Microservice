package com.illud.redalert.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.illud.redalert.domain.enumeration.Alert;

/**
 * A UserDetails.
 */
@Entity
@Table(name = "user_details")
public class UserDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "mobile_num")
    private Double mobileNum;

    @Enumerated(EnumType.STRING)
    @Column(name = "alert")
    private Alert alert;

    @OneToMany(mappedBy = "userDetails")
    private Set<FriendList> friends = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public UserDetails userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDetails firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDetails lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public UserDetails password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getMobileNum() {
        return mobileNum;
    }

    public UserDetails mobileNum(Double mobileNum) {
        this.mobileNum = mobileNum;
        return this;
    }

    public void setMobileNum(Double mobileNum) {
        this.mobileNum = mobileNum;
    }

    public Alert getAlert() {
        return alert;
    }

    public UserDetails alert(Alert alert) {
        this.alert = alert;
        return this;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public Set<FriendList> getFriends() {
        return friends;
    }

    public UserDetails friends(Set<FriendList> friendLists) {
        this.friends = friendLists;
        return this;
    }

    public UserDetails addFriends(FriendList friendList) {
        this.friends.add(friendList);
        friendList.setUserDetails(this);
        return this;
    }

    public UserDetails removeFriends(FriendList friendList) {
        this.friends.remove(friendList);
        friendList.setUserDetails(null);
        return this;
    }

    public void setFriends(Set<FriendList> friendLists) {
        this.friends = friendLists;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDetails userDetails = (UserDetails) o;
        if (userDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserDetails{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", password='" + getPassword() + "'" +
            ", mobileNum=" + getMobileNum() +
            ", alert='" + getAlert() + "'" +
            "}";
    }
}
