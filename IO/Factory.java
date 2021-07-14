//alice aidlin 206448326
//maayan nadivi 208207068
package IO;

import Country.*;
import Location.Location;
import Population.Person;

import java.util.List;

public class Factory {
    public Settlement Get_Settlement(String settelment_type, String name, Location location, List<Person> people, RamzorColor ramzorColor, Map map) {
        if (settelment_type != null) {
            switch (settelment_type) {
                case "City":
                    return new City(name, location, people, ramzorColor, map);
                case "Moshav":
                    return new Moshav(name, location, people, ramzorColor, map);
                case "Kibbutz":
                    return new Kibbutz(name, location, people, ramzorColor, map);
            }
        }
        return null;
    }
}
