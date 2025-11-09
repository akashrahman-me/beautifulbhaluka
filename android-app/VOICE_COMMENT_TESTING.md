# Voice Comment Feature - Testing Guide

## Quick Test Steps

### 1. Basic Voice Recording Test

1. Navigate to any post's comments section
2. Long-press the microphone icon (bottom left)
3. ✅ Should see:
    - Pulsing red dot animation
    - "Recording voice..." text
    - Timer counting up (00:01, 00:02, etc.)
    - "← Slide to cancel" text

4. Release the microphone button
5. ✅ Should automatically:
    - Stop recording
    - Hide recording UI
    - Submit the voice comment
    - Show comment in the list

### 2. Cancel Recording Test

1. Long-press the microphone icon
2. While holding, slide your finger to the LEFT
3. ✅ Should see:
    - Text changes to "Release to cancel"
    - Microphone icon turns RED
4. Release while in cancel zone
5. ✅ Should:
    - Cancel recording
    - NOT submit comment
    - Return to normal state

### 3. Permission Test (First Time Only)

1. Fresh install or first time using voice
2. Long-press microphone icon
3. ✅ Should see:
    - Android permission dialog for microphone
4. Grant permission
5. ✅ Should automatically start recording

6. If denied:
    - Should show error message
    - Microphone button should still be clickable to retry

### 4. UI State Tests

#### When Text is Empty:

- ✅ Image icon: visible, enabled
- ✅ Microphone icon: visible, enabled, full opacity
- ✅ Send button: visible, disabled

#### When Typing Text:

- ✅ Image icon: visible, enabled
- ✅ Microphone icon: visible, disabled, 75% opacity
- ✅ Send button: visible, enabled

#### When Recording:

- ✅ Image icon: visible, disabled, 30% opacity
- ✅ Microphone icon: visible, showing as active
- ✅ Text input: disabled
- ✅ Recording indicator: visible at top

#### When Images Selected:

- ✅ Image icon: colored blue/primary
- ✅ Microphone icon: disabled, 75% opacity
- ✅ Send button: enabled

### 5. Animation Tests

1. Start recording
2. ✅ Verify:
    - Red dot pulses smoothly (scales 1.0 → 1.3 → 1.0)
    - Red dot opacity changes (1.0 → 0.5 → 1.0)
    - Animation repeats continuously
    - No stuttering or lag

### 6. Edge Cases

#### Very Short Recording:

1. Quick tap and release (< 1 second)
2. ✅ Should still record and submit

#### Long Recording:

1. Hold for 60+ seconds
2. ✅ Timer continues counting
3. ✅ Recording continues normally

#### Multiple Comments:

1. Record and submit voice comment
2. Try recording another immediately
3. ✅ Should work without issues
4. ✅ Previous recording file cleaned up

#### App Background:

1. Start recording
2. Press home button (app to background)
3. ✅ Recording should cancel automatically (handled by system)

### 7. File System Check

After recording:

1. Check device: `data/data/com.akash.beautifulbhaluka/cache/recordings/`
2. ✅ Should see M4A files with timestamps
3. ✅ Canceled recordings should be deleted
4. ✅ Submitted recordings should remain (until backend upload)

## Expected Behavior Summary

### Visual Feedback

- ✅ Pulsing red dot with smooth animation
- ✅ Real-time duration display
- ✅ Clear cancel zone indication
- ✅ Icon state changes based on context
- ✅ No blinking or disappearing icons

### Audio Quality

- ✅ Clear voice recording
- ✅ No background noise (device-dependent)
- ✅ Consistent volume levels
- ✅ AAC codec, M4A format

### Performance

- ✅ No UI lag during recording
- ✅ Smooth animations at 60fps
- ✅ Quick response to gestures
- ✅ Minimal battery impact

## Common Issues & Solutions

### Issue: Permission dialog doesn't appear

**Solution:**

- Check AndroidManifest.xml has RECORD_AUDIO permission
- Verify app has not been permanently denied permission
- Test on Android 6.0+ device

### Issue: Recording doesn't start

**Solution:**

- Check microphone hardware
- Verify no other app is using microphone
- Check logcat for errors
- Ensure Hilt is properly initialized

### Issue: Icons blink when recording starts

**Solution:**

- Already fixed: Icons are always rendered
- Use `alpha` modifier instead of conditional rendering

### Issue: Cancel gesture not working

**Solution:**

- Ensure sliding LEFT (not right)
- Slide at least 100dp from button
- Check gesture detection logs

## Logcat Filters

To monitor voice recording:

```
adb logcat | grep -E "(VoiceRecorder|CommentsViewModel|MediaRecorder)"
```

## Performance Metrics

Expected values:

- Recording start latency: < 200ms
- File save time: < 100ms
- UI response time: < 16ms (60fps)
- Memory usage: < 50MB during recording

## Testing Checklist

- [ ] Basic recording works
- [ ] Cancel gesture works
- [ ] Permission flow works
- [ ] Animations smooth
- [ ] Icons don't blink
- [ ] Duration updates correctly
- [ ] Files saved correctly
- [ ] Canceled recordings deleted
- [ ] Multiple recordings work
- [ ] Works with text comments
- [ ] Works with image comments
- [ ] Send button always visible
- [ ] UI states correct

## Ready for Production? ✅

Once all tests pass:

1. Add backend API integration
2. Implement file upload progress
3. Add retry logic for uploads
4. Consider audio compression
5. Add maximum duration limit
6. Implement audio preview (optional)

---

**Current Status:** Core functionality complete and ready for testing!
**Backend Integration:** Pending - currently saves local M4A files
**Production Ready:** After backend integration ✅

