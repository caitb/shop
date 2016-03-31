    private ComUser comUser;
    private ComUserAccount comUserAccount;
    private Map<String, Object> wxAgentPro;

    public ComUser getComUser() {
        return comUser;
    }

    public void setComUser(ComUser comUser) {
        this.comUser = comUser;
    }

    public ComUserAccount getComUserAccount() {
        return comUserAccount;
    }

    public void setComUserAccount(ComUserAccount comUserAccount) {
        this.comUserAccount = comUserAccount;
    }

    public Map<String, Object> getWxAgentPro() {
        return wxAgentPro;
    }

    public void setWxAgentPro(Map<String, Object> wxAgentPro) {
        this.wxAgentPro = wxAgentPro;
    }

    @Override
    public String toString() {
        return "User{" +
                "comUser=" + comUser +
                ", comUserAccount=" + comUserAccount +
                ", wxAgentPro=" + wxAgentPro +
                '}';
    }