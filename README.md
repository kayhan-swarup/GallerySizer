# GallerySizer

Add this to your project's gradle file 

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  
Add the dependency to your build.gradle:

	dependencies {
          ...
	        implementation 'com.github.kayhan-swarup:GallerySizer:1.0.0'
	}
  
Inside your fragment call the method  startGalleryIntent() on user action:		

		GalleryPicker picker = GalleryPicker.getInstance();
		picker.startGalleryIntent(((MainActivity)getActivity()), GalleryFragment.this);

And last but not the least... inside the onActivityResult method add this line:		

		picker.onResult(requestCode, resultCode, data, size);

        
        

  
  
  
