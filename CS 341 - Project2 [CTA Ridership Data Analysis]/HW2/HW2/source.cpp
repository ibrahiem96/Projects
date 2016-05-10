//CTA "L" RidershipAnalysis: main source.cpp
//
// Ibrahiem Mohammad
// U. of Illinois, Chicago
// CS341, Spring 2016
// HW #2

#include <iostream>
#include <iomanip> 
#include <fstream>
#include <string> 
#include <sstream> 
#include <vector> 
#include <algorithm>

using namespace std;

class ActiveStopsData
{
public:
	int station_ID;
	string stationName;
	string stopName;
	string Direction;

	int rides_U;
	int rides_W;
	int rides_A;

	ActiveStopsData(int station_id, string sation_name, string stop_name, string direction)
		: station_ID(station_id), stationName(sation_name), stopName(stop_name), Direction(direction)
	{ 
		rides_U = 0;
		rides_W = 0;
		rides_A = 0;
	}

	
};
	
class Day
{
public:
	string Type;
	int totalRides;

	Day(string type, int total_rides)
		: Type(type), totalRides(total_rides)
	{ }
};


//functions to read in data from csv files
	vector<ActiveStopsData> readInStationData(string filename, int &numStops) {
	fstream file(filename);
	string	line;

	if (!file.good())
		throw std::invalid_argument("file '" + filename + "' not found!");

	getline(file, line);  // discard first line: column headers
	
		vector<ActiveStopsData> active_container;

		while (getline(file, line)) {
			stringstream ss(line);

			string station_id, direction, stop_name, station_name, junk;
			getline(ss, station_id, ',');
			getline(ss, direction, ',');
			getline(ss, stop_name, ',');
			getline(ss, station_name, ',');
			getline(ss, junk);  // the rest of the line just in case:

			int id = stoi(station_id);
			//cout << id << "," << direction << "," << station_name << endl;

			ActiveStopsData entry(id, station_name, stop_name, direction);

			numStops++;

			auto end = find_if(active_container.begin(), active_container.end(), [=](ActiveStopsData entry) {
				if (entry.stationName.compare(station_name) == 0) return true;
				else return false;
			}
			);

			if (end == active_container.end()) {
				active_container.push_back(entry);
			}

		}
		return active_container;
	}

	vector<ActiveStopsData> readInRidershipData(string filename, vector<ActiveStopsData> &V, int &total_ridership) {
				
		fstream file(filename);
		string	line;

		if (!file.good())
			throw std::invalid_argument("file '" + filename + "' not found!");

		getline(file, line);  // discard first line: column headers
		while (getline(file, line)) {
			stringstream ss(line);

			string station_id, service_date, day_type, total_rides, junk;
			getline(ss, station_id, ',');
			getline(ss, service_date, ',');
			getline(ss, day_type, ',');
			getline(ss, total_rides, ',');
			getline(ss, junk);  // the rest of the line just in case:

			int id = stoi(station_id);
			int totalRides = stoi(total_rides);

			//cout << id << "," << service_date << "," << totalRides << "," << day_type << endl;

			auto end = find_if(V.begin(), V.end(), [=](ActiveStopsData entry/*, string day_type*/) {
				if (id == entry.station_ID) return true;
				else return false;
			}
			);


			int ph = distance(V.begin(), end);

			if (end != V.end()) {
				total_ridership += totalRides;

				if (day_type == "U") V[ph].rides_U += totalRides;
				if (day_type == "W") V[ph].rides_W += totalRides;
				if (day_type == "A") V[ph].rides_A += totalRides;
			}

			
		}
		return V;
	}

	void print_A(vector<ActiveStopsData> V) {
		
		sort(V.begin(), V.end(), [](ActiveStopsData a, ActiveStopsData b)
		{
			if (a.rides_A > b.rides_A) return true;
			else /*if (a.rides_A < b.rides_A)*/ return false;
		}
		);

		cout << "Top-5 Ridership on Saturdays" << endl;
		for (int i = 0; i < 5; i++) {
			cout << (i + 1) << ". " << V[i].stationName << V[i].rides_A << endl;
		}
		cout << endl;
	}

	void print_W(vector<ActiveStopsData> V) {

		sort(V.begin(), V.end(), [](ActiveStopsData a, ActiveStopsData b)
		{
			if (a.rides_W > b.rides_W) return true;
			else /*if (a.rides_A < b.rides_A)*/ return false;
		}
		);

		cout << "Top-5 Ridership on Weekdays" << endl;
		for (int i = 0; i < 5; i++) {
			cout << (i + 1) << ". " << V[i].stationName << V[i].rides_W << endl;
		}
		cout << endl;
	}

	void print_U(vector<ActiveStopsData> V) {

		sort(V.begin(), V.end(), [](ActiveStopsData a, ActiveStopsData b)
		{
			if (a.rides_U > b.rides_U) return true;
			else /*if (a.rides_A < b.rides_A)*/ return false;
		}
		);

		cout << "Top-5 Ridership on Holidays/Sundays" << endl;
		for (int i = 0; i < 5; i++) {
			cout << (i + 1) << ". " << V[i].stationName << V[i].rides_U << endl;
		}
		cout << endl;

	}

	

int main() {

	int numStops = 0;
	int total_ridership = 0;

	string file1 = "CTA-L-Stops.csv";
	string file2 = "CTA-L-Ridership-Daily-2weeks.csv";

	vector<ActiveStopsData> V;

	V = readInStationData(file1, numStops);
	readInRidershipData(file2, V, total_ridership);

	cout << "** CTA L Ridership Analysis Program **" << endl;
	cout << endl;

	cout << "Inputting stations and stops from 'CTA-L-Stops.csv'..." << endl;
	cout << "Num Stations: " << V.size() - 1 << endl;
	cout << "Num Stops: " << numStops << endl;
	cout << endl;

	cout << "Inputting ridership data from 'CTA-L-Ridership-Daily-2weeks.csv'..." << endl;
	cout << "Total Ridership: " << total_ridership << endl;
	cout << endl;

	print_A(V);
	print_W(V);
	print_U(V);
	

}


