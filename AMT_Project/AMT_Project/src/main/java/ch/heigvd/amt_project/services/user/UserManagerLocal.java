
package ch.heigvd.amt_project.services.user;

import ch.heigvd.amt_project.model.User;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UserManagerLocal {

    public long create(User user);

    public List<User> read();
    public User read(long id);
    public List<User> readUserByFirstName(String firstName);
    public List<User> readUserByLastName(String lastName);
    public List<User> readUserByEmail(String email);
    public List<User> readUserByOrgId(long orgId);
    public User readContactByOrgId(long orgId);
    
    public User update(User user);

    public void delete(User user);
}