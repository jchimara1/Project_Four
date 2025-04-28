import React from 'react';

const VehicleInputForm = () => {
    return (
        <div>
            <form>
                <label>Make:
                <input />
                </label>
                <label>Model:
                    <input />
                </label>
                <label>Year:
                    <input />
                </label>
                <label>Price:
                    <input />
                </label>
                <label>Used:
                    <input type="checkbox" />
                </label>
                <button>Submit</button>
            </form>
        </div>
    );
};

export default VehicleInputForm;