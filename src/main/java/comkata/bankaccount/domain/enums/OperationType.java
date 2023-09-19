package comkata.bankaccount.domain.enums;

public enum OperationType {
    DEPOSIT("DEPOSIT"),
    WITHDRAW("WITHDRAW");

    private final String string;

    OperationType(final String string){
        this.string = string;
    }

    public String toString(){
        return string;
    }
}
