# **Food Menu Management System**

This **Food Menu Management System** allows users to manage and track their food orders seamlessly with a **Graphical User Interface (GUI)** for easy interaction and a **Command Line Interface (CLI)** for backend operations. It integrates **file handling** using **CSV files** to store user-specific cart details and central order logs. The system ensures accurate inventory management, preventing customers from adding out-of-stock items and validating input for item quantities.

---

## **Project Description**

This project provides an efficient and user-friendly solution for managing food orders, supporting the following features:

- **GUI & CLI Integration**: Start with a GUI to display the menu and pending orders, with the option to switch to the Command Line Interface (CLI) for more detailed cart management.
- **File Handling**: Cart details are saved in individual **CSV files** for each user, while orders are logged centrally.
- **Stock Management**: Ensures only in-stock items can be added to the cart, enforcing accurate stock levels.
- **Dynamic Cart Operations**: Add, modify, and track items in the cart with live price updates.
- **Error Handling**: Prevents invalid inputs like negative quantities and ensures out-of-stock items are not added to the cart.

This system is ideal for managing food orders in small restaurants or food delivery platforms, providing robust error handling and real-time cart updates.

---

## **Features**

### **Part 1: Graphical User Interface (GUI)**
- The application starts with a **GUI** that displays the food menu and any pending orders.
- The GUI must be **closed** before switching to the **Command Line Interface (CLI)** for further operations.

### **Part 2: File Handling**
- **Cart Details**: Stored in files named in the format `cart_<username>.csv` for each user.
- **Order Logging**: All order details are stored centrally in a file named `kavya.csv`.
- The system uses **.csv files** for input/output operations to manage cart and order data.

---

## **Core Functionalities**

### **Out-of-Stock Items**
- The system prevents customers from adding out-of-stock food items to their cart.
- **Testing**:
  - Attempt to add an unavailable item (e.g., Burger) and ensure it is not added to the cart.
  - Ensures that the system properly enforces stock availability rules.
  - If an out-of-stock item is attempted to be added, **an error is raised**.

### **Cart Operations**

#### **Adding Items**
- Adds available food items to the cart and updates the total price accurately.
- **Testing**:
  - Verify the new total price after adding an item to the cart is equal to the previous price plus the item price multiplied by its quantity.
  - Example: Adding an in-stock item (e.g., Pizza) updates the total as `item price × quantity`.

#### **Modifying Quantities**
- Dynamically updates the total price when modifying item quantities in the cart.
- **Testing**:
  - Add multiple items (e.g., Pizza and Fries), change the quantity of an item, and verify that the total price is correctly recalculated.

#### **Invalid Quantities**
- Prevents the addition of items with negative quantities.
- **Testing**:
  - Attempt to add an item with a negative quantity.
  - Ensures that the action is rejected and only valid quantities are accepted.
  - Returns **false** if a negative quantity is entered.

---

## **Error Handling**

- **Out-of-Stock Items**: An error is raised if an attempt is made to add out-of-stock items to the cart.
- **Invalid Quantities**: Prevents adding items with invalid (negative) quantities to the cart.

---

## **File Structure**

- **Cart Files**: User-specific cart details are stored in files named `cart_<username>.csv`.
- **Order File**: All orders are logged centrally in the file `kavya.csv`.

---

## **Testing**

- The system includes tests for:
  - Adding items and updating the total price.
  - Modifying item quantities and recalculating totals dynamically.
  - Ensuring that out-of-stock items are not added to the cart.
  - Rejecting negative quantities to prevent invalid operations.

---

## **How to Run**

1. Launch the **GUI** to view the menu and pending orders.
2. Close the GUI to proceed to the **CLI** for detailed cart management.
3. Use the CLI to add items, modify quantities, and manage orders.
```

This format and structure present your project clearly and concisely, making it suitable for sharing on GitHub!
