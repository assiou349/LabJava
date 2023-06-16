package com.labjava.skillguest.api.persistence.interfaces;

public interface INotificationEntity  extends IDto{

    String getTechEmail();
    String getRequesterEmail();
    String getDescription();
    String getSubjet();
}
