package edu.iu.habahram.coffeeorder.model;

public class Expresso extends Beverage{

            @Override
            public String getDescription() {
                return "Expresso";
            }
            @Override
            public float cost() {
                return 1.34F;
            }
}
