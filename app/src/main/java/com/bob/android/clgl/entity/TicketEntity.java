package com.bob.android.clgl.entity;

/**
 * @package com.bob.android.clgl.entity
 * @fileName TicketEntity
 * @Author Bob on 2018/6/19 21:02.
 * @Describe TODO
 */

public class TicketEntity {

    private String ticketPswd;
    private String usedTime;
    private String department;
    private String status;

    public String getTicketPswd() {
        return ticketPswd;
    }

    public void setTicketPswd(String ticketPswd) {
        this.ticketPswd = ticketPswd;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
