package com.cupme.repository.projection;

public interface UserView {
    /**
     * projection for user, only the id is needed
     * @return Long
     */
    Long getId();
    String getFirstName();
    String getLastName();
}
