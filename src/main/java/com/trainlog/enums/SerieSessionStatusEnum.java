package com.trainlog.enums;

public enum SerieSessionStatusEnum {
    COMPLETED("finalizada"), NOT_COMPLETED("não finalizada");

    private String status;

    SerieSessionStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SerieSessionStatusEnum fromStatus(String status){
        for(SerieSessionStatusEnum sessionStatus: SerieSessionStatusEnum.values()){
            if(sessionStatus.getStatus().equalsIgnoreCase(status)){
                return  sessionStatus;
            }
        }
        throw new IllegalArgumentException("Status inválido");
    }
}
