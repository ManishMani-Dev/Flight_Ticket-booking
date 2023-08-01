package com.example.AirLine.Model;
public enum Quality {
    Premium{
        @Override
        public int getPercentage() {
            return 100;
        }
    },
    Medium{
        @Override
        public int getPercentage() {
            return 50;
        }
    },
    Basic{
        @Override
        public int getPercentage() {
            return 25;
        }
    };
    public abstract int getPercentage();
}
