package api.stepsDefinition;

import com.otchi.application.AccountService;
import com.otchi.domain.users.exceptions.AccountAlreadyExistsException;
import com.otchi.domain.users.models.Account;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class AccountCreationStepsDefinitions {

    @Autowired
    private AccountService accountService;

    @Given("^following users exist:$")
    public void followingUsersExist(DataTable dataTable) throws Throwable {
        dataTable.asList(CucumberAccount.class)
                .stream()
                .map(CucumberAccount::toDomainAccount)
                .forEach(
                        account -> {
                            try {
                                accountService.createAccount(account, Optional.empty());
                            } catch (AccountAlreadyExistsException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );

    }

    private class CucumberAccount {
        private String username;
        private String firstName;
        private String lastName;

        public Account toDomainAccount() {
            return new Account(firstName, lastName, username, "password", "en");
        }
    }
}
