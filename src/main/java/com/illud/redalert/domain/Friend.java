package com.illud.redalert.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.illud.redalert.domain.enumeration.Status;

/**
 * A Friend.
 */
@Entity
@Table(name = "friend")
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "friend_id")
    private String friendId;

    @Column(name = "accepted")
    private Boolean accepted;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

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

    public Friend userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public Friend friendId(String friendId) {
        this.friendId = friendId;
        return this;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public Boolean isAccepted() {
        return accepted;
    }

    public Friend accepted(Boolean accepted) {
        this.accepted = accepted;
        return this;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Status getStatus() {
        return status;
    }

    public Friend status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        Friend friend = (Friend) o;
        if (friend.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), friend.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Friend{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", friendId='" + getFriendId() + "'" +
            ", accepted='" + isAccepted() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
