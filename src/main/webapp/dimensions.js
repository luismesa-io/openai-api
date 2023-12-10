const fetchDimensions = async model => {
    const response = await fetch('resources/v1/dimensions/' + model);
    const dimensions = await response.json();
    const dimensionsEvent = new CustomEvent('dimensions-event', {
        detail: {
            payload: dimensions
        },
        bubbles: true
    });
    window.dispatchEvent(dimensionsEvent);
    return dimensions;

};

export { fetchDimensions };