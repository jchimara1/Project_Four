import {React, useState} from 'react';
import {Vehicle} from "../types/Vehicle.ts";
import {create} from "../frontend_service/VehicleService.ts";

type vehicleInputFormProps = {
    submitHandler:any
}


const VehicleInputForm = ({submitHandler}:vehicleInputFormProps) => {
    const initialFormData:Vehicle = {id:null, make: "" , model:"", year:-1, price:-1, used: false }
    const [formData, setFormData] = useState(initialFormData)


    const handleSubmit = (e) => {
        const newVehicle:Vehicle = formData
        e.preventDefault()
        submitHandler(newVehicle)
    }


    return (
        <div>
            <form onSubmit={handleSubmit}>
                <label>Make:
                <input onChange={(e) => setFormData({...formData, ["make"]: e.target.value})}/>
                </label>
                <label>Model:
                    <input  onChange={(e) => setFormData({...formData, ["model"]: e.target.value})}/>
                </label>
                <label>Year:
                    <input  onChange={(e) => setFormData({...formData, ["year"]: +e.target.value})}/>
                </label>
                <label>Price:
                    <input onChange={(e) => setFormData({...formData, ["price"]: +e.target.value})}/>
                </label>
                <label>Used:
                    <input type="checkbox" onChange={(e) => setFormData({...formData, ["used"]: e.target.checked})} />
                </label>
                <button>Submit</button>
            </form>
        </div>
    );
};

export default VehicleInputForm;