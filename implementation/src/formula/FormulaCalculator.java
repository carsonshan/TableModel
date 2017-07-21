package formula;

/**
 * Created by Philipp on 25.05.2017.
 */


/**
 * Calculates the numerical prepared String-formula
 * @param <T>
 */

public class FormulaCalculator<T> {

    private int size;
    private Node<T> head;

    /**
     * final calculation
     */

    public FormulaCalculator() {
        head = null;
        size = 0;
    }


    /**
     * push the to the stack
     * @param element
     */

    public void push(double element) {
        if (head == null) {
            head = new Node(element);
        } else {
            Node<T> newNode = new Node(element);
            newNode.next = head;
            head = newNode;
        }

        size++;
    }

    /**
     * pop from the stack
     * @return
     */

    public T pop() {
        if (head == null)
            return null;
        else {
            T topData = head.data;

            head = head.next;
            size--;

            return topData;
        }
    }



    /**
     * close the calculation
     * @return
     */

    public T top() {
        if (head != null)
            return head.data;
        else
            return null;
    }


    /**
     * save solution of subcalculation of Formula
     * @param <T>
     */
    private class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }

    }

}
