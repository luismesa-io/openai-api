const fetchStyles = async model => {
    const response = await fetch('resources/v1/styles/' + model);
    const styles = await response.json();
    const stylesEvent = new CustomEvent('styles-event', {
        detail: {
            payload: styles
        },
        bubbles: true
    });
    window.dispatchEvent(stylesEvent);
    return styles;
};

export { fetchStyles };