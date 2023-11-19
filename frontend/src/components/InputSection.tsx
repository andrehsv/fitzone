import { ChangeEvent, FormEvent, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function () {
	const navigate = useNavigate();
	let [code, setCode] = useState("");

	let tokenObject = {
		token: "",
	};

	const handleSubmit = async () => {
		localStorage.clear();
		await fetch("http://localhost:8080/api/v1/login", {
			method: "POST",
			body: code,
		})
			.then((response) => response.json())
			.then((response) => {
				tokenObject = response;
				localStorage.setItem(
					"authentication_token",
					tokenObject.token.toString()
				);
				navigate("/user");
			})
			.catch(({ message }) => window.alert(message));
	};

	return (
		<form onSubmit={(e: FormEvent<HTMLFormElement>) => e.preventDefault()}>
			<div className="form-group">
				<input
					type="tel"
					className="form-control text-center"
					maxLength={4}
					placeholder="- - - -"
					id="input-code-field"
					value={code}
					onChange={({ target }) => setCode(target.value)}
				/>
			</div>
			<div className="row p-2">
				<button
					type="submit"
					className="btn btn-primary mt-3"
					onClick={handleSubmit}
				>
					Entrar
				</button>
			</div>
		</form>
	);
}
