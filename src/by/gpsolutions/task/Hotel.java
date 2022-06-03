package by.gpsolutions.task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Hotel {

    private String name;
    private String price;
    private String addressLine;
    private String city;
    private String country;
    private String state;

}
