By following our architecture
In the `places` screen, instead of section type titlebar, use our `screentopbar` reuable component

And then when user click on palce card it should open a `details` screens, so implement a details screen. In the details screen will show a markdown preview.

here's example data

```markdown
# ড্রিম হাউস পার্ক

![Image](https://beautifulbhaluka.com/wp-content/uploads/2024/12/IMG_20241231_162524-300x221.jpg)

ড্রিম হাউস পার্ক ময়মনসিংহ জেলার ভালুকা উপজেলার কাঁঠালি এলাকায় অবস্থিত একটি বিনোদন কেন্দ্র। এটি পরিবার এবং বন্ধুদের সঙ্গে সময় কাটানোর জন্য একটি চমৎকার স্থান। পার্কটিতে বিভিন্ন আকর্ষণীয় স্থাপনা এবং বিনোদনমূলক সুবিধা রয়েছে, যা দর্শনার্থীদের মনোরঞ্জন করে।

![Image](https://beautifulbhaluka.com/wp-content/uploads/2024/12/IMG_20241231_162542-228x300.jpg)

ড্রিম হাউস পার্ক ভালুকা উপজেলার কাঁঠালি এলাকায় অবস্থিত। ঢাকা থেকে সরাসরি বাস বা ব্যক্তিগত যানবাহনে ভালুকা পৌঁছানো যায়। ভালুকা থেকে স্থানীয় যানবাহনে কাঁঠালি এলাকায় পার্কে পৌঁছানো সম্ভব।

![Image](https://beautifulbhaluka.com/wp-content/uploads/2024/12/IMG_20241231_162559-300x198.jpg)

পার্কটিতে বিভিন্ন বিনোদনমূলক রাইড, সুইমিং পুল, এবং প্রাকৃতিক সৌন্দর্য উপভোগের ব্যবস্থা রয়েছে। এটি পরিবার এবং বন্ধুদের সঙ্গে সময় কাটানোর জন্য একটি আদর্শ স্থান।

![Image](https://beautifulbhaluka.com/wp-content/uploads/2024/12/IMG_20241231_162617-300x215.jpg)
```

---

In the `restuarant` screen, implement `publish` screen where user will add restuarent details.

in the publish screen will have those field:

-   নাম
-   ঠিকানা
-   লোকেশন
-   মোবাইল নাম্বার
-   ছবি।

however, in the `restuarant` card will have a rating option so that anyone can rate this out of 5 star. we don't need to add any note in the rating time.

---
