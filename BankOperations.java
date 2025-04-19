package Tasks;
class BankOperations {
    private double balance;
    private String transactionHistory;

    public BankOperations() {
        this.balance = 1000.00;  // Initial balance for demo purposes
        this.transactionHistory = "Transaction History:\n";
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory += "Deposited: Rs" + amount + "\n";
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory += "Withdrew: Rs" + amount + "\n";
        } else {
            transactionHistory += "Insufficient balance for withdrawal of: Rs" + amount + "\n";
        }
    }

    public void transfer(String toAccount, double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory += "Transferred: Rs" + amount + " to account " + toAccount + "\n";
        } else {
            transactionHistory += "Insufficient balance for transfer of: Rs" + amount + "\n";
        }
    }

    public String getTransactionHistory() {
        return transactionHistory;
    }

    public double getBalance() {
        return balance;
    }
}
