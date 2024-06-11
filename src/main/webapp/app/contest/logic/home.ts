import axios from 'axios';

export async function getDishes() {
  try {
    const response = await axios.get('api/dishes');

    // Check for valid response data before processing
    if (!response.data || !Array.isArray(response.data)) {
      throw new Error('Invalid dishes data received from API');
    }

    const formattedDishes = response.data.map(dish => {
      if (dish.image && dish.imageContentType) {
        dish.formattedImage = `data:${dish.imageContentType};base64,${dish.image}`;
      } else {
        dish.formattedImage = null; // Set to null for consistency
      }
      return dish;
    });

    return formattedDishes;
  } catch (error) {
    console.error('Error fetching dishes:', error);
    return []; // Return an empty array for error handling in Vue component
  }
}

function base64ToImage(base64: any, contentType: any) {
  const image = new Image();
  image.src = `data:${contentType};base64,${base64}`;
  return image;
}
