package model.citydata;

public class Wind {
    private String direction;
    private String strength;

    public Wind() {
    }

    public Wind(String direction, String strength) {
        this.direction = direction;
        this.strength = strength;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "direction='" + direction + '\'' +
                ", strength='" + strength + '\'' +
                '}';
    }
}
