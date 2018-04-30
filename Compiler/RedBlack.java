// Inserts numbers (presently 0 through 19) into a red-black tree.
// Prints the tree after each insertion.  Then repeatedly attempts to
// delete the root element of the tree and element 'i' (where 'i' ranges
// from 0 to 19) until the tree is empty; the tree is printed after each
// attempted deletion.

// main class -- contains method that performs insertions and deletions
class Main extends Lib {
    
    // method that inserts and deletes elements
    public void main() {
	// create empty red-black tree
	RedBlackTree root = new NullRedBlackTree();
	
	// number of elements
	int numElements = 20;
	
	// insert the elements, lowest-to-highest
	for (int i = 0; i < numElements; i++) {
	    printStr("========== insert ");
	    printInt(i);
	    printStr("===========\n");
	    root = root.insert(i);
	    root.print();
	}
	
	// alternately attempt to delete the root and the ith number
	for (int i = 0; !root.isNull(); i++) {
	    printStr("========== delete root ===========\n");
	    root = root.delete(root.asNonNull().data);
	    root.print();
	    printStr("========== attempt to delete ");
	    printInt(i);
	    printStr("===========\n");
	    root = root.delete(i);
	    root.print();
	}
    }
    
}

// red-black tree class (should be abstract)
class RedBlackTree {
    
    // is it a null red-black tree (default = false)
    public boolean isNull() {
	return false;
    }
    
    // is it a black red-black tree (default = true)
    public boolean isBlack() {
	return true;
    }
    
    // set the tree-color to black
    public void setBlack() {
    }
    
    // is the color red?
    public boolean isRed() {
	return !isBlack();
    }
    
    // helper method: returns modified tree with element inserted
    public RedBlackTree helpInsert(int data) {
	return null; // abstract method: need to override
    }
    
    // return modified tree with element inserted
    public RedBlackTree insert(int data) {
	// insert
	RedBlackTree insertResult = helpInsert(data);
	// set root to black and return
	insertResult.setBlack();
	return insertResult;
    }
    
    // "casts" to a non-null red-black-tree (returns null of not a non-null one
    public NonNullRedBlackTree asNonNull() {
	return null;
    }
    
    // print the tree
    public void print() {
	Lib lib = new Lib();
	this.helpPrint(null, lib);
    }
    
    // helper method for printing tree
    public void helpPrint(BooleanList bl, Lib lib) {
    }
    
    // look up an element in the tree, tell whether found
    public boolean lookup(int data) {
	return false; // default value
    }
    
    // return version of tree with element deleted
    public RedBlackTree delete(int data) {
	return helpDelete(data, new boolean[1]);
    }
    
    // helper method: returns version of tree with element deleted; 'isShort'
    // is a one-element array whose element gets set to 'true' if the
    // new tree is shorter (in the black sense) than the original.
    public RedBlackTree helpDelete(int data, boolean[] isShort) {
	return this;
    }
}

// an empty red-black tree
class NullRedBlackTree extends RedBlackTree {
    
    // it is null
    public boolean isNull() {
	return true;
    }
    
    // result of inserting is s red node with the approriate data
    public RedBlackTree helpInsert(int data) {
	return new NonNullRedBlackTree().init(data,this,this,false);
    }
}

class NonNullRedBlackTree extends RedBlackTree {
    
    // instance variables
    int data; // the data
    RedBlackTree left; // left subtree
    RedBlackTree right; // right subtree
    boolean isBlack; // color
    
    // is the color black?
    public boolean isBlack() {
	return isBlack;
    }
    
    // set the color to black
    public void setBlack() {
	isBlack = true;
    }
    
    // set the color to red
    public void setRed() {
	isBlack = false;
    }
    
    // pseudo-constructor: initializes instance variables via parameters
    public NonNullRedBlackTree init(int data, RedBlackTree left,
				    RedBlackTree right, boolean isBlack) {
	this.data = data;
	this.left = left;
	this.right = right;
	this.isBlack = isBlack;
	return this;
    }
    
    // helper-function for inserting
    public RedBlackTree helpInsert(int data) {
	// default return-value is the original tree (in the case
	// where the data is already in the tree
	RedBlackTree rtnVal = this;
	
	// insert to left or right, depending on value
	if (data > this.data) {
	    // insert to right; then rebalance via right
	    right = right.helpInsert(data);
	    rtnVal = this.rebalanceAfterInsert(false);
	}
	else if (data < this.data) {
	    // insert to left, then rebalance via left
	    left = left.helpInsert(data);
	    rtnVal = this.rebalanceAfterInsert(true);
	}
	
	// return the resulting value
	return rtnVal;
    }
    
    // tree lookup
    public boolean lookup(int data) {
	boolean rtnVal = true;
	if (data < this.data) { // data smaller: look left
	    rtnVal = left.lookup(data);
	}
	else if (data > this.data) { // data larger: look right
	    rtnVal = right.lookup(data);
	}
	return rtnVal;
    }
    
    // helper-method to delete a node; the item in the 1-element array
    // 'isShort' gets set to 'true' if the subtree's black height was
    // reduced
    public RedBlackTree helpDelete(int data, boolean[] isShort) {
	
	// return-value
	RedBlackTree rtnVal = null;
	
	// unbalanced return-value
	NonNullRedBlackTree rtnVal2 = this;
	
	// if found element remove proper item
	if (data == this.data) { // found element
	    if (right.isNull()) {
		if (left.isNull()) {
		    // both children null
		    isShort[0] = isBlack; // mark short if removing black node
		    rtnVal = right; // return a null one					
		}
		else {
		    // right child null; left null/red, so return it as black
		    rtnVal = left;
		    rtnVal.setBlack();
		}
	    }
	    else if (left.isNull()) {
		// right child null; left null/red, so return it as black
		rtnVal = right;
		rtnVal.setBlack();
	    }
	    else { // both children non-null: do inorder-successor swap
		NonNullRedBlackTree succ = right.asNonNull();
		for (;;) { // find inorder successor
		    NonNullRedBlackTree next = succ.left.asNonNull();
		    if (next == null) break;
		    succ = next;
		}
		
		// swap data with inorder successor
		int temp = succ.data;
		succ.data = this.data;
		this.data = temp;
		
		// go delete our data (now in leaf or semileaf position
		rtnVal2.right = right.helpDelete(data, isShort);
		
		// adjust tree after deletion to ensure balanced
		rtnVal = rtnVal2.adjustAfterDelete(true, isShort);
	    }
	}
	else if (data < this.data) { // data is smaller
	    // delete left and rebalance
	    rtnVal2.left = left.helpDelete(data, isShort);
	    rtnVal = rtnVal2.adjustAfterDelete(false, isShort);
	}
	else { // data is larger
	    // delete right and rebalance
	    rtnVal2.right = right.helpDelete(data, isShort);
	    rtnVal = rtnVal2.adjustAfterDelete(true, isShort);
	}
	
	return rtnVal;
    }
    
    // adjust after deletion
    // - doMirrorImage means that we need to do the mirror-image transformations
    // - isShort's single element tells whether a shortness has occurred
    public NonNullRedBlackTree adjustAfterDelete(boolean doMirrorImage,
						 boolean[] isShort) {
	
	// default node to return is the current one
	NonNullRedBlackTree rtnVal = this;
	
	if (isShort[0]) { // only need to transform is shortness occurred
	    
	    //////////////////////////////////////////////////////////////
	    // Transformation numbers correspond to those in Vegdahl's
	    // CS 411 notes (slide-set 16), from the Spring 2010 version
	    // of the course.
	    //////////////////////////////////////////////////////////////
	    
	    // default is that shortness does not propagate up
	    isShort[0] = false;
	    
	    // get parent, sibling and nephews
	    NonNullRedBlackTree parent = this;
	    NonNullRedBlackTree sibling =
		parent.getRight(doMirrorImage).asNonNull();
	    RedBlackTree innerNephew = sibling.getLeft(doMirrorImage);
	    RedBlackTree outerNephew = sibling.getRight(doMirrorImage);
	    
	    // apply transformation
	    if (parent.isRed()) { // red parent, black sibling
		if (innerNephew.isBlack()) {
		    // transformation #1: inner nephew is black
		    parent.setRight(innerNephew, doMirrorImage);
		    sibling.setLeft(parent, doMirrorImage);
		    rtnVal = sibling;
		}
		else if (outerNephew.isRed()) {
		    // transformation #3: inner and outer nephews both red
		    parent.setRight(innerNephew, doMirrorImage);
		    sibling.setLeft(parent, doMirrorImage);
		    parent.setBlack();
		    sibling.setRed();
		    outerNephew.setBlack();
		    rtnVal = sibling;
		}
		else {
		    // transformation #4: inner nephew red, outer nephew black
		    NonNullRedBlackTree innerNephewNonNull = innerNephew.asNonNull();
		    parent.setRight(innerNephewNonNull.getLeft(doMirrorImage),
				    doMirrorImage);
		    sibling.setLeft(innerNephewNonNull.getRight(doMirrorImage),
				    doMirrorImage);
		    innerNephewNonNull.setLeft(parent, doMirrorImage);
		    innerNephewNonNull.setRight(sibling, doMirrorImage);
		    sibling.setRed();
		    innerNephew.setBlack();
		    rtnVal = sibling;
		}
	    }
	    else if (sibling.isRed()) { // black parent, red sibling
		NonNullRedBlackTree innerNephewNonNull = innerNephew.asNonNull();
		RedBlackTree innerGrandNephew =
		    innerNephewNonNull.getLeft(doMirrorImage);
		if (innerGrandNephew.isRed()) {
		    // transformation #7: newphews black, inner-grand-nephew red
		    NonNullRedBlackTree innerGrandNephewNonNull =
			innerGrandNephew.asNonNull();
		    parent.setRight(innerGrandNephewNonNull.getLeft(doMirrorImage),
				    doMirrorImage);
		    innerNephewNonNull.setLeft(
					       innerGrandNephewNonNull.getRight(doMirrorImage),
					       doMirrorImage);
		    innerGrandNephewNonNull.setBlack();
		    rtnVal = innerGrandNephewNonNull;
		}
		else {
		    // transformation #8: newphews black, inner-grand-nephew black
		    parent.setRight(innerNephewNonNull.getLeft(doMirrorImage),
				    doMirrorImage);
		    innerNephewNonNull.setLeft(parent, doMirrorImage);
		    parent.setRed();
		    sibling.setBlack();
		    rtnVal = sibling;
		}
	    }
	    else { // parent and sibling both black
		if (outerNephew.isRed()) {
		    // transformation #6: outer nephew red
		    parent.setRight(sibling.getLeft(doMirrorImage), doMirrorImage);
		    sibling.setLeft(parent, doMirrorImage);
		    outerNephew.asNonNull().setBlack();
		    rtnVal = sibling;
		}
		else if (innerNephew.isRed()) {
		    // transformation #5: outer nephew black, inner nephew red
		    NonNullRedBlackTree innerNephewNonNull = innerNephew.asNonNull();//b
		    parent.setRight(innerNephewNonNull.getLeft(doMirrorImage),
				    doMirrorImage); // a right <- b left
		    sibling.setLeft(innerNephewNonNull.getRight(doMirrorImage),
				    doMirrorImage); // c left <- b right
		    innerNephewNonNull.setLeft(parent, doMirrorImage);
		    innerNephewNonNull.setRight(sibling, doMirrorImage);
		    innerNephewNonNull.setBlack();
		    rtnVal = innerNephewNonNull;
		}
		else {
		    // transformation #2": both newphews black
		    sibling.setRed();
		    isShort[0] = true; // mark shortness as occurring
		}
		
	    }
	}
	
	// return the appropriate value
	return rtnVal;
    }
    
    // converts self to non-null tree (trivial)
    public NonNullRedBlackTree asNonNull() {
	return this;
    }
    
    // return logical "right" node (based on 'doMirrorImage'
    public RedBlackTree getRight(boolean doMirrorImage) {
	RedBlackTree rtnVal = right;
	if (doMirrorImage) {
	    rtnVal = left;
	}
	return rtnVal;
    }
    
    // return logical "left" node (based on 'doMirrorImage'
    public RedBlackTree getLeft(boolean doMirrorImage) {
	return getRight(!doMirrorImage);
    }
    
    // set logical "right" node, based on "doMirrorImage"
    public void setRight(RedBlackTree node, boolean doMirrorImage) {
	if (doMirrorImage) {
	    left = node;
	}
	else {
	    right = node;
	}
    }
    
    // set logical "left" node, based on "doMirrorImage"
    public void setLeft(RedBlackTree node, boolean doMirrorImage) {
	setRight(node, !doMirrorImage);
    }
    
    // returns rebalanced version of tree after an insertion; assumes insertion
    // was done to (logical) rightmost grandchild
    // - 'doMirrorImage' is true iff the insertion occurred from the left
    public RedBlackTree rebalanceAfterInsert(boolean doMirrorImage) {
	
	//////////////////////////////////////////////////////////////
	// Transformation numbers correspond to those in Vegdahl's
	// CS 411 notes (slide-set 16), from the Spring 2010 version
	// of the course.
	//////////////////////////////////////////////////////////////		
	
	// get root and "logical" left and right children
	RedBlackTree rtnVal = this;
	RedBlackTree myRight = getRight(doMirrorImage);
	RedBlackTree myLeft = getLeft(doMirrorImage);
	
	if (myRight.isRed()) { // black parent, red right child
	    RedBlackTree myRightRight = myRight.asNonNull().getRight(doMirrorImage);
	    RedBlackTree myRightLeft = myRight.asNonNull().getLeft(doMirrorImage);
	    if (myRightRight.isRed()) { // outer red-red
		if (myLeft.isRed()) {
		    // transformations #1 and #2: red parent, red uncle
		    this.isBlack = false;
		    myLeft.setBlack();
		    myRight.setBlack();
		}
		else {
		    NonNullRedBlackTree r = myRight.asNonNull();
		    if (r.getRight(doMirrorImage).isRed()) {
			// # transformation #3: outer of red parent, black uncle
			rtnVal = r;
			this.setRight(r.getLeft(doMirrorImage), doMirrorImage);
			r.setLeft(this, doMirrorImage);
			this.setRed();
			r.setBlack();
		    }
		    else if (r.getLeft(doMirrorImage).isRed()) {
			// # transformation #4: inner of red parent, black uncle
			NonNullRedBlackTree rl = r.getLeft(doMirrorImage).asNonNull();
			rtnVal = rl;
			r.setLeft(rl.getRight(doMirrorImage), doMirrorImage);
			rl.setRight(r, doMirrorImage);
			this.setRight(rl.getLeft(doMirrorImage), doMirrorImage);
			rl.setLeft(this, doMirrorImage);
			this.setRed();
			rl.setBlack();						
		    }
		}
	    }
	}
	return rtnVal;
    }
    
    public void helpPrint(BooleanList bl, Lib lib) {
	right.helpPrint(new BooleanList().init(true, bl), lib);
	helpPrintIndent(bl, lib);
	if (bl == null) {
	    lib.printStr("---");
	}
	else if (bl.val) {
	    lib.printStr(" /-");
	}
	else {
	    lib.printStr(" \\-");
	}
	if (isBlack()) {
	    lib.printStr("#");
	}
	else {
	    lib.printStr("*");
	}
	lib.printInt(data);
	lib.printStr("\n");
	left.helpPrint(new BooleanList().init(false, bl), lib);
    }
    
    public void helpPrintIndent(BooleanList bl, Lib lib) {
	if (bl != null) {
	    if (bl.next != null) {
		helpPrintIndent(bl.next, lib);
		if (bl.val == bl.next.val) {
		    lib.printStr("   ");
		}
		else {
		    lib.printStr("|  ");
		}
	    }
	    else {
		lib.printStr("   ");
	    }
	}
    }
}

class BooleanList {
    boolean val;
    BooleanList next;
    
    public BooleanList init(boolean val, BooleanList next) {
	this.val = val;
	this.next = next;
	return this;
    }
}

