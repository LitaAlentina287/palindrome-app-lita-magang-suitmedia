# Suitmedia Internship Test App - Lita

## Documentation  
You can access all screenshots and the APK file via the following Google Drive link:  
**https://drive.google.com/drive/folders/1RfvHo9XMvJv_w2IoAsON5efWkteHsArk?usp=drive_link**

---

## About the App

This is an Android application developed as part of the **Suitmedia Internship Test** by **Lita**. It consists of **3 main screens** that demonstrate user input handling, string logic (palindrome check), API integration, dynamic screen updates, and RecyclerView pagination.

---

## Features

### 1. First Screen – Palindrome Checker

- Contains two **EditText** fields:
  - One for **user name input**
  - One for **sentence input** to check if it's a palindrome
- A **“CHECK”** button:
  - Validates whether the input is a palindrome
  - Displays a dialog message:  
    - “isPalindrome” ✅ if true  
    - “not palindrome” ❌ if false  
  - Examples:
    - `isPalindrome("kasur rusak") → true`
    - `isPalindrome("step on no pets") → true`
    - `isPalindrome("suitmedia") → false`
- A **“NEXT”** button:
  - Navigates to the **Second Screen**
  - Passes the entered name

---

### 2. Second Screen – Welcome & Selection

- Displays:
  - Static text: **“Welcome”**
  - Dynamic text: shows **user's name** from First Screen
  - Another dynamic text: shows the **Selected User Name** (initially empty)
- A **“CHOOSE A USER”** button:
  - Navigates to the **Third Screen**
  - Awaits user selection

---

### 3. Third Screen – User List (API Integration)

- Displays list of users using **RecyclerView**
- Fetches user data from:  
  `https://reqres.in/api/users`
- Shows each user's:
  - First name
  - Last name
  - Email
  - Avatar
- Includes:
  - **Pull to refresh**
  - **Pagination** on scroll (using `page` & `per_page`)
  - **Empty state** if no users available
- On user click:
  - Selected user’s name is sent back to Second Screen
  - Updates **Selected User Name** dynamically  
  *(does not navigate to a new screen)*

---

## Technologies Used

- **Language:** Kotlin  
- **UI Design:** XML Layouts  
- **Networking:** Retrofit  
- **Image Loading:** Glide  
- **Architecture:** Simple MVVM pattern  
- **SDK Target:** 21 – 34  

---

## How to Run

1. Clone or download this repository.
2. Open it with **Android Studio**.
3. Connect a device or emulator.
4. Click **Run**, or build APK via **Build > Build Bundle(s)/APK(s)**.

---

## Author

**Lita**  
Built for the 2025 **Suitmedia Internship Test**.

