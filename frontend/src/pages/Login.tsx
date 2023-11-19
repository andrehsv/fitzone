import logo from "../assets/logo.png";
import InputSection from "../components/InputSection";

export default function () {
	return (
		<>
			<div className="card position-absolute top-50 start-50 translate-middle text-center shadow-lg">
				<img src={logo} className="img-fluid pt-3" id="logo" />
				<div className="card-body">
					<h1 className="fw-bold">Fitzone Gym</h1>
                    <p>Insira seu código de acesso</p>
					<InputSection />
				</div>
			</div>
		</>
	);
}
