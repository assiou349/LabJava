package com.labjava.skillguest.api.persistence.interfaces;

//Todo : Une interface d'entité qui étend ton interface de DTO ?
public interface INotificationEntity  extends IDto{

    String getTechEmail();
    String getRequesterEmail();
    String getDescription();
    String getSubjet();

    void setTechEmail(String email);
}
