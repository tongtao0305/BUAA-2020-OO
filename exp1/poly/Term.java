public class Term {
    private int coef;
    private int index;

    public Term(int c, int i) {
        this.coef = c;
        this.index = i;
    }

    public int getCoef() {
        return coef;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        String ret;

        if (coef == 0) {
            return "0";
        } else if (coef != 0 && index == 0) {
            return String.valueOf(coef);
        } else if (coef == 1 && index != 1) {
            return "x^" + index;
        } else if (coef == 1 && index == 1) {
            return "x";
        } else if (coef == -1 && index != 1) {
            return "-x^" + index;
        } else if (coef == -1 && index == 1) {
            return "-x";
        } else if (index == 1) {
            return coef + "*x";
        }

        if (coef > 0) {
            ret = String.valueOf(coef);
        } else if (coef < 0) {
            ret = String.valueOf(coef);
        } else {
            ret = "0";
        }

        if (index == 0) {
            return ret;
        } else if (index == 1) {
            return ret + "x";
        }
        return ret + "*x^" + index;
    }

    // code for comparable
    public int compareTo(Term o) {
        // the first key : index
        if (this.index < o.getIndex()) {
            return 1;
        }
        if (this.index > o.getIndex()) {
            return -1;
        }

        // the second key : coef
        if (this.coef > o.getCoef()) {
            return 1;
        }
        if (this.coef < o.getCoef()) {
            return -1;
        }

        return 0;
    }
}