import static org.junit.Assert.fail;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeAll;

// TODO: Add tests to test the tree is balanced or not

// @SuppressWarnings("rawtypes")
public class BALSTTest {

  BALST<String, String> balst1;
  BALST<Integer, String> balst2;

  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  void setUp() throws Exception {
    balst1 = createInstance();
    balst2 = createInstance2();
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterEach
  void tearDown() throws Exception {
    balst1 = null;
    balst2 = null;
  }

  protected BALST<String, String> createInstance() {
    return new BALST<String, String>();
  }

  protected BALST<Integer, String> createInstance2() {
    return new BALST<Integer, String>();
  }

  /**
   * Insert three values in sorted order and then check the root, left, and right keys to see if
   * rebalancing occurred.
   */
  @Test
  void testBALST_001_insert_sorted_order_simple() {
    try {
      balst2.insert(10, "10");
      if (!balst2.getKeyAtRoot().equals(10))
        fail("avl insert at root does not work");

      balst2.insert(20, "20");
      if (!balst2.getKeyOfRightChildOf(10).equals(20))
        fail("avl insert to right child of root does not work");

      balst2.insert(30, "30");
      Integer k = balst2.getKeyAtRoot();
      if (!k.equals(20))
        fail("avl rotate does not work");

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child

      Assert.assertEquals(balst2.getKeyAtRoot(), Integer.valueOf(20));
      Assert.assertEquals(balst2.getKeyOfLeftChildOf(20), Integer.valueOf(10));
      Assert.assertEquals(balst2.getKeyOfRightChildOf(20), Integer.valueOf(30));

      System.out.println("-- Test 001 --");
      balst2.print();
      System.out.println("--------------");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * Insert three values in reverse sorted order and then check the root, left, and right keys to
   * see if rebalancing occurred in the other direction.
   */
  @Test
  void testBALST_002_insert_reversed_sorted_order_simple() {
    try {
      balst2.insert(30, "30");
      if (!balst2.getKeyAtRoot().equals(30))
        fail("avl insert at root does not work");
      
      balst2.insert(20, "20");
      if (!balst2.getKeyOfLeftChildOf(30).equals(20))
        fail("avl insert to left child of root does not work");
      
      balst2.insert(10, "10");
      Integer k = balst2.getKeyAtRoot();
      if (!k.equals(20))
        fail("avl rotate does not work");

      System.out.println("-- Test 002 --");
      balst2.print();
      System.out.println("--------------");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * Insert three values so that a right-left rotation is needed to fix the balance.
   * 
   * Example: 10-30-20
   * 
   * Then check the root, left, and right keys to see if rebalancing occurred in the other
   * direction.
   */
  @Test
  void testBALST_003_insert_smallest_largest_middle_order_simple() {
    try {
      balst2.insert(10, "10");
      if (!balst2.getKeyAtRoot().equals(10))
        fail("avl insert at root does not work");
      
      balst2.insert(30, "30");
      if (!balst2.getKeyOfRightChildOf(10).equals(30))
        fail("avl insert to right child of root does not work");

      balst2.insert(20, "20");
      if (!balst2.getKeyAtRoot().equals(20))
        fail("avl right-left rotate does not work");


      System.out.println("-- Test 003 --");
      balst2.print();
      System.out.println("--------------");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * Insert three values so that a left-right rotation is needed to fix the balance.
   * 
   * Example: 30-10-20
   * 
   * Then check the root, left, and right keys to see if rebalancing occurred in the other
   * direction.
   */
  @Test
  void testBALST_004_insert_largest_smallest_middle_order_simple() {
    try {
      balst2.insert(30, "30");
      if (!balst2.getKeyAtRoot().equals(30))
        fail("avl insert at root does not work");
      
      balst2.insert(10, "10");
      if (!balst2.getKeyOfLeftChildOf(30).equals(10))
        fail("avl insert to right child of root does not work");
      
      balst2.insert(20, "20");
      if (!balst2.getKeyAtRoot().equals(20))
        fail("avl right-left rotate does not work");

      System.out.println("-- Test 004 --");
      balst2.print();
      System.out.println("--------------");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }
  
  /**
   * Insert 3 values and make sure get() works properly on all values
   * 
   */
  @Test
  void testBALST_005_insert_3_check_get_method() {
    try {
      for(int i = 0; i < 3; i++) {
        balst2.insert(i+1, Integer.toString(i+1));
      }
      
      for(int i = 0; i < 3; i++) {
        if(!balst2.get(i+1).equals(Integer.toString(i+1))) {
          fail("avl get() does not work");
        }
      }
      
      System.out.println("-- Test 005 --");
      balst2.print();
      System.out.println("--------------");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }
  
  /**
   * Insert 25 values and make sure get() works properly on all values
   * 
   */
  @Test
  void testBALST_006_insert_25_check_get_method() {
    try {
      for(int i = 0; i < 25; i++) {
        balst2.insert(i+1, Integer.toString(i+1));
      }
      
      for(int i = 0; i < 25; i++) {
        if(!balst2.get(i+1).equals(Integer.toString(i+1))) {
          fail("avl get() does not work");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }
  
  /**
   * Insert 100 values and make sure get() works properly on all values
   * 
   */
  @Test
  void testBALST_007_insert_100_check_get_method() {
    try {
      for(int i = 0; i < 100; i++) {
        balst2.insert(i+1, Integer.toString(i+1));
      }
      
      for(int i = 0; i < 100; i++) {
        if(!balst2.get(i+1).equals(Integer.toString(i+1))) {
          fail("avl get() does not work");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }
  
  /**
   * Insert 1000 values and make sure get() works properly on all values
   * 
   */
  @Test
  void testBALST_008_insert_1000_check_get_method() {
    try {
      for(int i = 0; i < 1000; i++) {
        balst2.insert(i+1, Integer.toString(i+1));
      }
      
      for(int i = 0; i < 1000; i++) {
        if(!balst2.get(i+1).equals(Integer.toString(i+1))) {
          fail("avl get() does not work");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  // Add tests to make sure that rebalancing occurs even if the
  // tree is larger. Does it maintain it's balance?
  // Does the height of the tree reflect it's actual height
  // Use the traversal orders to check.

  // Does delete work? Does the tree maintain balance when a key is deleted?

}
