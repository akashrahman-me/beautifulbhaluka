# 📘 অ্যাপ ফিচার স্পেসিফিকেশন (Developer-Friendly)

এই ডকুমেন্টটি ডেভেলপারদের জন্য সাজানো হয়েছে যাতে **প্রতিটি মডিউল, ফিচার এবং দায়িত্ব পরিষ্কারভাবে বোঝা যায়**। প্রতিটি অংশে **ফিচার তালিকা, ইউজার স্টোরি, টেকনিক্যাল নোট** আলাদা করে দেওয়া হয়েছে।

---

## 🏠 Home + UI

### উদ্দেশ্য

প্রাথমিক UI লেআউট ও কনটেন্ট কন্ট্রোল।

### ফিচারস

-   **ক্যাটাগরি ম্যানেজমেন্ট**

    -   উপজেলা, পৌরসভা, ইউনিয়ন, থানা, উপজেলা প্রশাসন, প্রসিদ্ধ বাজার, ভ্রমণ রাডার, মুদি হেডার রাডার ইত্যাদি।
    -   ➡️ **শুধু Admin দ্বারা এডিটেবল**।
    -   অন্যান্য ক্যাটাগরি → সব ইউজারের জন্য উন্মুক্ত।
    -   ইউজারের কনটেন্ট Admin approval এর পর পাবলিশ হবে।

### টেকনিক্যাল নোট

-   **Role-Based Access Control (RBAC)**
-   ডাটাবেজ টেবিল: `categories`
-   কলাম: `id, name, editable_by`

---

## 🌐 সোশ্যাল মডিউল

### ইউজার প্রোফাইল

**User Story:** একজন ইউজার নিজের প্রোফাইল তৈরি/এডিট করতে পারবে।

-   নাম, জন্মতারিখ, বায়ো, কভার ফটো
-   ভেরিফাইড টিক (Admin verification এর মাধ্যমে)
-   ফেসবুক স্টাইল "About", Photos, Activity log
-   **Who viewed my profile (Paid Feature)**

**DB Schema:** `users`, `profiles`

### ফলো / ফ্রেন্ড সিস্টেম

**User Story:** ইউজার অন্যকে ফলো/ফ্রেন্ড করতে পারবে।

-   Follow/Unfollow, Friend/Unfriend
-   Block/Unblock
-   Suggestions Engine (Mutual friends, interest-based)
-   **Secret Crush / Hidden Like**
-   **Anonymous Q\&A Box**

**DB Schema:** `relationships`

### কনটেন্ট ইন্টারঅ্যাকশন

-   Reaction (❤️ 😂 😡 কাস্টম রিঅ্যাকশন সাপোর্ট)
-   Comment + Reply
-   Share (Post/Story)
-   Mention (@user), Stickers, Video comments
-   Download toggle (Admin/user controlled)

**DB Schema:** `posts`, `comments`, `reactions`

### চ্যাট সিস্টেম

-   প্রাইভেট চ্যাট
-   গ্রুপ চ্যাট + কল
-   Encryption + Read Receipts
-   Self-destruct messages (optional)
-   Media recording & sharing

**DB Schema:** `messages`, `groups`

### গেমিফিকেশন

-   Truth or Dare Mode
-   Mood Sharing (Sad, Happy, Angry, Lonely)
-   Polls & Quizzes
-   Auto Translate (Real-Time Chat/Post)
-   Leaderboard (Top users, helpers, creators)
-   Daily Challenges + Rewards (XP, Boost, Blue Tick unlock)
-   Viral Content Promotion system
-   Professional Profile option (Teacher, Lawyer, Doctor)

### লাইভ স্ট্রিমিং

-   লাইভ ভিডিও/অডিও + রেকর্ডিং
-   Join Request + Live Chat
-   Replay (up to 30 days)

**Tech Stack:** WebRTC / RTMP Server

### বিশেষ ফিচার

-   Nearby Help (SOS → Notify users within 2km)
-   Friend Radar (GPS based)
-   Secret Crush
-   Music Room (Spotify/YouTube Sync)
-   Sponsorship System (Boosted posts)

---

## 🛒 ই-কমার্স মডিউল

### Seller Side

-   Seller Registration + Verification
-   Product Listing (Images, Price, Stock, Conditions)
-   Order Management
-   Seller-Deliveryman communication

### Buyer Side

-   Product Search + Filters
-   Wishlist + Cart
-   Order Tracking + Payment (Bkash, Nagad, Card, COD)

### Admin Side

-   Seller Verification
-   Dispute Resolution
-   Analytics Dashboard
-   Fraud Control (Fake sellers/products)

**DB Schema:** `products`, `orders`, `transactions`

---

## 🎓 ছাত্রছাত্রী মডিউল

### ফিচারস

-   Profile (Religion, DOB, Education, Address, Mobile)
-   Search + Filters (Religion, Upazila, Gender, Marital status)
-   Shortlist/Favorites
-   Chat/Call System (In-app + optional phone reveal)
-   Profile Privacy Control
-   Married Badge + Status
-   Highlighted Profiles
-   Archive Mode

### Success Stories

-   Counter Tracking (DB: `success_stories`)
-   Profile Status → Active / Married / Deactivated
-   Married counter auto +1

---

## 💼 চাকরি মডিউল

### Employer Side

-   Company Registration + Verification
-   Job Posting + Management
-   Direct Shortlisting + Interview Scheduling
-   Candidate Rating System

### Candidate Side

-   Profile (Education, Skills, Experience)
-   CV Builder
-   Saved Jobs
-   Application Tracking (Pending / Shortlisted / Selected)

### Admin Side

-   Verification of Employers/Candidates
-   Complaint Handling
-   Fake Account Deletion
-   Analytics + Reporting (Job posting %, candidate activity)
-   Top Employer Ranking
-   Success Counter (How many got hired)

**DB Schema:** `jobs`, `applications`, `companies`

---

## 🏘 বাসা ভাড়া মডিউল

### Owner Side

-   Add/Edit/Delete Property (Flat, Single room, Sublet, Hostel)
-   Manage Availability (Hidden/Visible)
-   Premium Ads

### User Side

-   Search by Location, Price, Type
-   Save Favorites
-   Contact via Chat/Call
-   Google Maps Integration
-   Notification for nearby listings

### Tech Notes

-   Google Maps API Integration
-   DB Schema: `properties`

---

## 🚗 গাড়ি ভাড়া মডিউল

### Owner Side

-   Register Car (Model, Type, Fuel, Driver Option)
-   Set Availability + Pricing (Per hour/day/km)
-   Premium Ads

### Customer Side

-   Search + Book Car
-   View Booking Details
-   Save Favorites
-   Chat/Call with Owner

### Extra

-   Reviews & Ratings
-   Live GPS Tracking (Optional)
-   Booking Notifications
-   Integration with Google Maps (Pickup → Destination)

**DB Schema:** `cars`, `car_bookings`

---

## 👨‍⚕️ অনলাইন ডাক্তার মডিউল

### Patient Side

-   Doctor Search + Filters (Name, Specialty, Location, Hospital)
-   Appointment Booking + Reminders
-   Online Consultation (Video/Audio/Chat)
-   Prescription Download (PDF)
-   Upload Medical Records
-   Health Profile (Weight, Height, Blood group, Allergies)
-   Review & Rating
-   Online Payment (Bkash, Nagad, Rocket, Card)

### Doctor Side

-   Profile + Verification (BMDC)
-   Schedule Management
-   Online Consultation
-   Prescription Writer
-   Income Reports

### Admin Side

-   Doctor Verification
-   Appointment Oversight
-   Analytics + Reporting
-   User Management (Block/Activate)

**Tech Notes:** WebRTC for Calls, PDF Generation for Prescription

---

## 📚 টিউশন মডিউল

### Parent/Student Side

-   Create Profile
-   Post Tuition Request (Subject, Class, Timing, Budget, Gender preference)
-   Search Teachers by Subject/Location
-   Review & Rating Teachers

### Teacher Side

-   Apply to Tuition Requests
-   Schedule Management
-   Chat with Parent
-   Application Notifications

### Admin Side

-   Verify Teachers (NID Upload)
-   Remove Fake Accounts
-   Analytics (Requests, Matches)

### Extra

-   Notifications (New Requests, Applications)
-   Bookmark Teachers
-   Teacher Verification Badge

**DB Schema:** `tuition_requests`, `teachers`, `reviews`

---

## ✅ Common System-Wide Features

-   Authentication (JWT/OAuth)
-   Role Management (Admin, User, Seller, Employer, Doctor, Teacher)
-   Notifications (Push, Email, SMS)
-   File Upload System (S3/GCS)
-   Multi-language Support (BN + EN)
-   Analytics Dashboard (Charts, Reports)
-   Security (Encryption, RBAC, Logs)

---
