//alice aidlin 206448326
//maayan nadivi 208207068
package Virus;
import java.awt.Color;
public enum Virus {
    BritishVariant("BritishVariant", new BritishVariant()),
    ChineseVariant("ChineseVariant", new ChineseVariant()),
    SouthAfricanVariant("SouthAfricanVariant", new SouthAfricanVariant());

    private final String string;
    private final IVirus vir;

    Virus(String string, IVirus vir) {
        this.string = string;
        this.vir = vir;

    }

    // func return virus type or -1
    public static int findvirus(IVirus v1) {
        System.out.println(Virus.values().length);
        System.out.println("v1 "+v1.toString());
        for (int i = 0; i < Virus.values().length; i++) {


            if ( Virus.values()[i].vir.isequals(v1)) {

                return i;
            }

        }
        return -1;

    }

    public static String getSouthAfricanVariantName() {
        return SouthAfricanVariant.toString();
    }

    public static String getBritishVariantName() {
        return BritishVariant.toString();
    }

    public static String getChineseVariantName() {
        return ChineseVariant.toString();
    }
    @Override
    public String toString() {

        return string;
    }

    public IVirus getv() {
        return vir;
    }

}