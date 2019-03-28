import org.apache.metamodel.*;

public class Connect{
    private final DataContext dataContext;
    private final UpdateableDataContext updateableDataContext;
    private final boolean writeable;
    public Connect(){
        dataContext = null;
        updateableDataContext = null;
        writeable = false;
    }


}
