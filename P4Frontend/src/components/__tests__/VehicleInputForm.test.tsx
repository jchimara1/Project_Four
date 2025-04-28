import { render, screen } from "@testing-library/react"
import { describe, it, expect, vi } from "vitest";
import VehicleInputForm from "../VehicleInputForm.tsx";
import {userEvent} from "@testing-library/user-event";
import {Vehicle} from "../../types/Vehicle.ts";



describe('vehicle input form', () => {
    const testVehicle:Vehicle = {id: null, make: "honda", model:"civic", price:1700.00, used: true, year:2017}

    it('should display an input form with fields for make, model, year, price, and used', () => {


        render(<VehicleInputForm submitHandler={()=> {}}/>)
        const makeInput = screen.getByLabelText("Make:")
        const modelInput = screen.getByLabelText("Model:")
        const yearInput = screen.getByLabelText("Year:")
        const priceInput = screen.getByLabelText("Price:")
        const usedInput = screen.getByLabelText("Used:")
        const submitButton = screen.getByRole("button", {name: "Submit"})
        expect(makeInput).toBeVisible()
        expect(modelInput).toBeVisible()
        expect(yearInput).toBeVisible()
        expect(priceInput).toBeVisible()
        expect(usedInput).toBeVisible()
        expect(submitButton).toBeVisible()
        expect(usedInput).not.toBeChecked()

    });


    it('should submit form when submit button pressed', async () => {
        const mockClick = vi.fn()
        render(<VehicleInputForm submitHandler={mockClick}/>)
        const makeInput = screen.getByLabelText("Make:")
        const modelInput = screen.getByLabelText("Model:")
        const yearInput = screen.getByLabelText("Year:")
        const priceInput = screen.getByLabelText("Price:")
        const usedInput = screen.getByLabelText("Used:")
        const submitButton = screen.getByRole("button", {name: "Submit"})
        await userEvent.type(makeInput, testVehicle.make)
        await userEvent.type(modelInput, testVehicle.model)
        await userEvent.type(priceInput, testVehicle.price.toString())
        await userEvent.type(yearInput, testVehicle.year.toString())
        await userEvent.click(usedInput)
        await userEvent.click(submitButton)
        expect(mockClick).toHaveBeenCalledExactlyOnceWith(testVehicle)
    });
});