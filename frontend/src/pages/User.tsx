import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function User() {
	const navigator = useNavigate();

	let [user, setUser] = useState({
		fullName: "",
		expiration: "",
		accountNonExpired: "",
		role: "",
	});

	const authenticationToken = localStorage.getItem("authentication_token");

	useEffect(() => {
		fetch("http://localhost:8080/api/v1/customer", {
			headers: {
				Authorization: `Bearer ${authenticationToken}`,
			},
		})
			.then((response) => response.json())
			.then((response) => {
				setUser(response);
			})
			.catch((e: Error) => {
				window.alert(e.message);
				navigator("/login");
			});
	}, []);

	return (
		<div className="card text-center position-absolute top-50 start-50 translate-middle text-center shadow-lg p-2">
			<p>Nome: {user.fullName}</p>
			<p>
				Data de expiração: {user.expiration === null && "Vazio"}
				{user.expiration}
			</p>
			<p>Conta expirada: {(!user.accountNonExpired).toString()}</p>
			<p>Função: {user.role}</p>
			{user.role === "ADMIN" && (
				<button className="btn btn-primary" onClick={() => navigator("/users")}>
					Ver usuários
				</button>
			)}
		</div>
	);
}
