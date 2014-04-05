LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

# OPENCV_CAMERA_MODULES:=on
# OPENCV_INSTALL_MODULES:=on

# include $(CVROOT)/jni/OpenCV.mk

include ..\..\..\..\NVPACK\OpenCV-2.4.5-Tegra-sdk-r2\sdk\native\jni\OpenCV.mk

LOCAL_MODULE    := sudokuhelper
LOCAL_LDLIBS +=  -llog -ldl

include $(BUILD_SHARED_LIBRARY)
